INSERT INTO PROMPT (id, content, response, timestamp) VALUES ('49ef2c30-3ddd-11ee-be56-0242ac120002', 'Please, generate this good news', 'Sure', CURRENT_TIMESTAMP());
INSERT INTO PROMPT (id, content, response, timestamp) VALUES ('5d82263a-3ddd-11ee-be56-0242ac120002', 'Please, generate this bad news', 'Of course', CURRENT_TIMESTAMP());

INSERT INTO NEWS (id, title, content, timestamp)
    VALUES ('874fc980-4054-11ee-be56-0242ac120002', 'Neutral News', 'These news are neutral', CURRENT_TIMESTAMP());

INSERT INTO FLAVOREDNEWS (id, flavored_Title, flavored_Content, flavor, prompt_id, news_id)
    VALUES (RANDOM_UUID(), 'Good News', 'These news are incredible', 0, '49ef2c30-3ddd-11ee-be56-0242ac120002', '874fc980-4054-11ee-be56-0242ac120002');
INSERT INTO FLAVOREDNEWS (id, flavored_Title, flavored_Content, flavor, prompt_id, news_id)
    VALUES (RANDOM_UUID(), 'Bad News', 'These news are horrible' , 1, '5d82263a-3ddd-11ee-be56-0242ac120002', '874fc980-4054-11ee-be56-0242ac120002');

