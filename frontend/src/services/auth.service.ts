import { http, tokenStore } from "./http";
import type { LoginRequest, RegisterRequest, TokenResponse } from "../types";

export async function login(payload: LoginRequest): Promise<TokenResponse> {
    const { data } = await http.post<TokenResponse>("/api/v1/auth/login", payload);
    tokenStore.setTokens(data);
    return data;
}

export async function register(payload: RegisterRequest): Promise<TokenResponse> {
    const { data } = await http.post<TokenResponse>("/api/v1/auth/register", payload);
    tokenStore.setTokens(data);
    return data;
}

export async function refresh(refreshToken: string): Promise<TokenResponse> {
    const { data } = await http.post<TokenResponse>("/api/v1/auth/refresh", { refreshToken });
    tokenStore.setTokens(data);
    return data;
}

export async function logout(): Promise<void> {
    try {
        await http.post("/api/v1/auth/logout");
    } finally {
        tokenStore.clearTokens();
    }
}
