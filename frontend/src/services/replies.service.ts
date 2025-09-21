import { http } from "./http";
import type { ReplyCreateRequest, ReplyDTO, ReplyUpdateRequest } from "../types";

export async function getRepliesForComment(commentId: number): Promise<ReplyDTO[]> {
    const { data } = await http.get<ReplyDTO[]>(`/api/v1/replies/comment/${commentId}`);
    return data;
}

export async function getReply(id: number): Promise<ReplyDTO> {
    const { data } = await http.get<ReplyDTO>(`/api/v1/replies/${id}`);
    return data;
}

export async function createReply(payload: ReplyCreateRequest): Promise<ReplyDTO> {
    const { data } = await http.post<ReplyDTO>("/api/v1/replies", payload);
    return data;
}

export async function updateReply(id: number, payload: ReplyUpdateRequest): Promise<ReplyDTO> {
    const { data } = await http.put<ReplyDTO>(`/api/v1/replies/${id}`, payload);
    return data;
}

export async function deleteReply(id: number): Promise<void> {
    await http.delete(`/api/v1/replies/${id}`);
}
