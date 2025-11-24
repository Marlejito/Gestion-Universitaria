import { Student, Professor, Course, Enrollment, Grade } from "@shared/schema";

// Adaptador para mapear campos.
// El backend ya envía los datos en el formato correcto (inglés), por lo que este adaptador
// actúa principalmente como una capa de tipado y validación simple.

export const toBackend = {
    student: (data: Student): Student => data,
    professor: (data: Professor): Professor => data,
    course: (data: Course): Course => data,
    enrollment: (data: Enrollment): Enrollment => data,
    grade: (data: Grade): Grade => data
};

export const toFrontend = {
    student: (data: Student): Student => data,
    professor: (data: Professor): Professor => data,
    course: (data: Course): Course => data,
    enrollment: (data: Enrollment): Enrollment => data,
    grade: (data: Grade): Grade => data
};
