import axios from "axios";
import type { TokenResponse } from "../types";

const baseURL = import.meta.env.VITE_API_URL || "http://localhost:8080";

export const http = axios.create({
    baseURL,
    withCredentials: false,
});

type Tokens = TokenResponse | null;
const TOKENS_KEY = "tokens";

function getTokens(): Tokens {
    const raw = localStorage.getItem(TOKENS_KEY);
    return raw ? (JSON.parse(raw) as TokenResponse) : null;
}

function setTokens(t: TokenResponse) {
    localStorage.setItem(TOKENS_KEY, JSON.stringify(t));
}
function clearTokens() {
    localStorage.removeItem(TOKENS_KEY);
}

http.interceptors.request.use((config) => {
    const t = getTokens();
    if (t?.accessToken) {
        config.headers = config.headers ?? {};
        (config.headers as any).Authorization = `Bearer ${t.accessToken}`;
    }
    return config;
});

let refreshing = false;
http.interceptors.response.use(
    (r) => r,
    async (error) => {
        const original = error.config as any;
        if (error?.response?.status === 401 && !original?._retry) {
            const t = getTokens();
            if (!t?.refreshToken || refreshing) {
                clearTokens();
                return Promise.reject(error);
            }
            try {
                refreshing = true;
                original._retry = true;
                const res = await axios.post(`${baseURL}/api/v1/auth/refresh`, {
                    refreshToken: t.refreshToken,
                });
                const newTokens = res.data as TokenResponse;
                setTokens(newTokens);
                original.headers = original.headers ?? {};
                original.headers.Authorization = `Bearer ${newTokens.accessToken}`;
                return http(original);
            } catch (e) {
                clearTokens();
                return Promise.reject(e);
            } finally {
                refreshing = false;
            }
        }
        return Promise.reject(error);
    }
);

export const tokenStore = { getTokens, setTokens, clearTokens };
