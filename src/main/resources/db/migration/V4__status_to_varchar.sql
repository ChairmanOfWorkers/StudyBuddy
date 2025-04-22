ALTER TABLE study_requests
ALTER COLUMN status
    TYPE VARCHAR(20)
    USING status::text;

DROP TYPE request_status;