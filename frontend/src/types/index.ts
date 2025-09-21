export type Gender = "MALE" | "FEMALE" | "OTHER";
export type Status = "TODO" | "IN_PROGRESS" | "DONE";
export type Label = "BUG" | "FEATURE" | "ENHANCEMENT" | "DOCUMENTATION" | "REFACTOR";

export interface UserDTO {
    id: number;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    gender: Gender;
    imageId: string | null;
}

export interface TaskDTO {
    id: number;
    title: string;
    description: string | null;
    status: Status;
    labels: Label[];
    creatorUserId: number;
    assigneeUserId: number | null;
    dueAt: string | null;
    position: number | null;
    createdAt: string;
    updatedAt: string;
}

export interface CommentDTO {
    id: number;
    taskId: number;
    authorUserId: number;
    message: string;
    createdAt: string;
    updatedAt: string;
}

export interface ReplyDTO {
    id: number;
    commentId: number;
    authorUserId: number;
    message: string;
    createdAt: string;
    updatedAt: string;
}

export interface TokenResponse {
    accessToken: string;
    refreshToken: string;
}

export interface LoginRequest {
    usernameOrEmail: string;
    password: string;
}
export interface RegisterRequest {
    username: string;
    password: string;
    email: string;
    firstName: string;
    lastName: string;
    gender: Gender;
}

export interface UserUpdateRequest {
    email?: string;
    firstName?: string;
    lastName?: string;
    gender?: Gender;
    imageId?: string | null;
}

export interface TaskCreateRequest {
    title: string;
    description?: string | null;
    status: Status;
    labels?: Label[];
    assigneeUserId?: number | null;
    dueAt?: string | null; // ISO
    position?: number | null;
}

export interface TaskUpdateRequest extends TaskCreateRequest {}

export interface CommentCreateRequest {
    taskId: number;
    authorUserId: number;
    message: string;
}
export interface CommentUpdateRequest {
    message: string;
}

export interface ReplyCreateRequest {
    commentId: number;
    authorUserId: number;
    message: string;
}
export interface ReplyUpdateRequest {
    message: string;
}
