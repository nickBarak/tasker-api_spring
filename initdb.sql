CREATE TABLE IF NOT EXISTS task (
    id serial primary key,
    content varchar(63),
    date timestamp with time zone,
    is_complete boolean
);

ALTER TABLE task ALTER COLUMN date SET DEFAULT current_timestamp;
ALTER TABLE task ALTER COLUMN isComplete SET DEFAULT false;