
-- DROP TABLE IF EXISTS public.students_courses;
-- DROP TABLE IF EXISTS public.groups;
-- DROP TABLE IF EXISTS public.students;
-- DROP TABLE IF EXISTS public.courses;

DROP TABLE IF EXISTS public.groups;
CREATE TABLE public.groups (
                               group_id serial NOT NULL,
                               group_name character varying(6) COLLATE pg_catalog."default" NOT NULL,
                               CONSTRAINT "GROUPS_pkey" PRIMARY KEY (group_id)
);


DROP TABLE IF EXISTS public.students;
CREATE TABLE IF NOT EXISTS public.students
(
    student_id serial NOT NULL,
    group_id integer,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    second_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);

DROP TABLE IF EXISTS public.courses;
CREATE TABLE public.courses (
                                course_id serial NOT NULL,
                                course_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
                                course_description character varying(510) COLLATE pg_catalog."default" NOT NULL,
                                CONSTRAINT "COURSES_pkey" PRIMARY KEY (course_id)
);

DROP TABLE IF EXISTS public.students_courses;
CREATE TABLE public.students_courses
(
    row_id serial NOT NULL,
    student_id integer NOT NULL references students(student_id) on DELETE cascade,
    course_id integer NOT NULL,
    CONSTRAINT students_courses_pkey PRIMARY KEY (row_id)
);

