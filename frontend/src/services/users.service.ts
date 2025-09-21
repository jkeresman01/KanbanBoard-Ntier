import { http } from "./http";
import type { Page } from "../types/page";
import type { UserDTO, UserUpdateRequest } from "../types";

export async function getUsers(params?: {
    page?: number;
    size?: number;
    sort?: string;
}): Promise<Page<UserDTO>> {
    const { data } = await http.get<Page<UserDTO>>("/api/v1/users", { params });
    return data;
}

export async function getUser(id: number): Promise<UserDTO> {
    const { data } = await http.get<UserDTO>(`/api/v1/users/${id}`);
    return data;
}

export async function updateUser(id: number, payload: UserUpdateRequest): Promise<UserDTO> {
    const { data } = await http.put<UserDTO>(`/api/v1/users/${id}`, payload);
    return data;
}

export async function deleteUser(id: number): Promise<void> {
    await http.delete(`/api/v1/users/${id}`);
}

export async function uploadProfileImage(userId: number, file: File): Promise<void> {
    const form = new FormData();
    form.append("file", file);
    await http.post(`/api/v1/users/${userId}/profile-image`, form, {
        headers: { "Content-Type": "multipart/form-data" },
    });
}

export async function getProfileImage(userId: number): Promise<Blob> {
    const { data } = await http.get(`/api/v1/users/${userId}/profile-image`, {
        responseType: "blob",
    });
    return data;
}
