
DROP TABLE IF EXISTS public.groups;
CREATE TABLE public.groups (
    group_id integer NOT NULL,
    group_name character(6) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "GROUPS_pkey" PRIMARY KEY (group_id)
);

DROP TABLE IF EXISTS public.students;
CREATE TABLE public.students (
    student_id integer NOT NULL,
    group_id integer,
    first_name character(255) COLLATE pg_catalog."default" NOT NULL,
    second_name character(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "STUDENTS_pkey" PRIMARY KEY (student_id)
);

DROP TABLE IF EXISTS public.courses;
CREATE TABLE public.courses (
    course_id integer NOT NULL,
    course_name character(255) COLLATE pg_catalog."default" NOT NULL,
    course_description character(510) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "COURSES_pkey" PRIMARY KEY (course_id)
);

DROP TABLE IF EXISTS public.students_courses;
CREATE TABLE public.students_courses
(
    row_id integer NOT NULL,
    student_id integer NOT NULL,
    course_name character(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_courses_pkey PRIMARY KEY (row_id)
);

