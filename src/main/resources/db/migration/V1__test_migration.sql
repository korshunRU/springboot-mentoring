ALTER TABLE users
    ADD address VARCHAR(255);

ALTER TABLE users
    ALTER COLUMN address
SET NOT NULL;