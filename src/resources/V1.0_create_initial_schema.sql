CREATE TABLE words (
    id CHAR(36) NOT NULL,
    word VARCHAR(45) NOT NULL,
    is_ref TINYINT(1) NOT NULL DEFAULT 0,
    idx INT AUTO_INCREMENT,
    PRIMARY KEY (id),
    CONSTRAINT words_index_cons UNIQUE(idx)
);

CREATE TABLE runs(
    id CHAR(36) NOT NULL,
    created TIMESTAMP,
);

CREATE TABLE doc_types (
    id char(36) NOT NULL,
    name VARCHAR(32) NOT NULL,
    ref_id CHAR(36),
    run_id CHAR(36) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT doc_types_ref_id_cons FOREIGN KEY (ref_id) REFERENCES words(id),
    CONSTRAINT doc_types_run_id_cons FOREIGN KEY (run_id) REFERENCES runs(id),
    CONSTRAINT doc_type_name_unique UNIQUE(name)
);

CREATE TABLE doc_type_words (
    id CHAR(36) NOT NULL,
    doc_type_id CHAR(36) NOT NULL,
    word_id CHAR(36) NOT NULL,
    run_id CHAR(36) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT doc_type_words_doc_type_id_cons FOREIGN KEY (doc_type_id) REFERENCES doc_types(id),
    CONSTRAINT doc_type_words_word_id_cons FOREIGN KEY (word_id) REFERENCES words(id),
    CONSTRAINT doc_type_words_run_id_cons FOREIGN KEY (run_id) REFERENCES runs(id),
    CONSTRAINT doc_type_words_doc_type_id_word_id_unique UNIQUE(doc_type_id, word_id)
);

CREATE TABLE docs (
    id CHAR(36) NOT NULL,
    doc_type_id CHAR(36),
    run_id CHAR(36) NOT NULL,
    created TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT docs_doc_type_id_cons FOREIGN KEY (doc_type_id) REFERENCES doc_types(id)
    CONSTRAINT docs_run_id_cons FOREIGN KEY (run_id) REFERENCES runs(id)
);

CREATE TABLE doc_words(
    id CHAR(36) NOT NULL,
    doc_id CHAR(36) NOT NULL,
    doc_type_word_id CHAR(36) NOT NULL,
    run_id CHAR(36) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT doc_words_doc_id_cons FOREIGN KEY (doc_id) REFERENCES docs(id),
    CONSTRAINT doc_words_doct_type_word_id_cons FOREIGN KEY (doc_type_word_id) REFERENCES doc_type_words(id),
    CONSTRAINT doc_words_run_id FOREIGN KEY (run_id) REFERENCES runs(id),
    CONSTRAINT doc_words_doc_id_doc_type_word_id_unique UNIQUE(doc_id, doc_type_word_id)
);
