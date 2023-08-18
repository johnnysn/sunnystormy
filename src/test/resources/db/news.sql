INSERT INTO PROMPT (id, content, response, timestamp) VALUES ('49ef2c30-3ddd-11ee-be56-0242ac120002', 'Please, generate this good news', 'Sure', CURRENT_TIMESTAMP());
INSERT INTO PROMPT (id, content, response, timestamp) VALUES ('5d82263a-3ddd-11ee-be56-0242ac120002', 'Please, generate this bad news', 'Of course', CURRENT_TIMESTAMP());

INSERT INTO NEWS (id, title, content, date, flavor, prompt_id) VALUES (RANDOM_UUID(), 'Good News', 'These news are incredible', CURRENT_DATE(), 0, '49ef2c30-3ddd-11ee-be56-0242ac120002');
INSERT INTO NEWS (id, title, content, date, flavor, prompt_id) VALUES (RANDOM_UUID(), 'Bad News', 'These news are horrible' , CURRENT_DATE(), 1, '5d82263a-3ddd-11ee-be56-0242ac120002');