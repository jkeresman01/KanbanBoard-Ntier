import { http } from "./http";
import type { CommentCreateRequest, CommentDTO, CommentUpdateRequest } from "../types";

export async function getCommentsForTask(taskId: number): Promise<CommentDTO[]> {
    const { data } = await http.get<CommentDTO[]>(`/api/v1/comments/task/${taskId}`);
    return data;
}

export async function getComment(id: number): Promise<CommentDTO> {
    const { data } = await http.get<CommentDTO>(`/api/v1/comments/${id}`);
    return data;
}

export async function createComment(payload: CommentCreateRequest): Promise<CommentDTO> {
    const { data } = await http.post<CommentDTO>("/api/v1/comments", payload);
    return data;
}

export async function updateComment(
    id: number,
    payload: CommentUpdateRequest
): Promise<CommentDTO> {
    const { data } = await http.put<CommentDTO>(`/api/v1/comments/${id}`, payload);
    return data;
}

export async function deleteComment(id: number): Promise<void> {
    await http.delete(`/api/v1/comments/${id}`);
}
