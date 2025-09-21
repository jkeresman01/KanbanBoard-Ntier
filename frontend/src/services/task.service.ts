import { http } from "./http";
import type { Status, TaskCreateRequest, TaskDTO, TaskUpdateRequest } from "../types";

export async function createTask(payload: TaskCreateRequest): Promise<TaskDTO> {
    const { data } = await http.post<TaskDTO>("/api/v1/tasks", payload);
    return data;
}

export async function getTask(id: number): Promise<TaskDTO> {
    const { data } = await http.get<TaskDTO>(`/api/v1/tasks/${id}`);
    return data;
}

export async function getTasks(status?: Status): Promise<TaskDTO[]> {
    const { data } = await http.get<TaskDTO[]>("/api/v1/tasks", {
        params: status ? { status } : undefined,
    });
    return data;
}

export async function updateTask(id: number, payload: TaskUpdateRequest): Promise<TaskDTO> {
    const { data } = await http.put<TaskDTO>(`/api/v1/tasks/${id}`, payload);
    return data;
}

export async function deleteTask(id: number): Promise<void> {
    await http.delete(`/api/v1/tasks/${id}`);
}
