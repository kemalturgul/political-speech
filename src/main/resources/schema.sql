CREATE TABLE IF NOT EXISTS political_speech(ID BIGINT auto_increment PRIMARY KEY,  speaker VARCHAR(255),  topic VARCHAR(255), date DATE, words INT);
CREATE INDEX IF NOT EXISTS topic_idx ON political_speech (`topic`);
CREATE INDEX IF NOT EXISTS date_idx ON political_speech (`date`);