
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (

	id INT NOT NULL AUTO_INCREMENT,
	profile_image_url VARCHAR(32) DEFAULT NULL,
	first_name VARCHAR(32) DEFAULT NULL,
	last_name VARCHAR(64) DEFAULT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(32) NOT NULL,
	PRIMARY KEY ( id ),
	UNIQUE KEY idx_unique_email ( email )

) ENGINE=InnoDB CHARSET=utf8;

INSERT INTO users (first_name,last_name,email,password) VALUES("John","Smith","jsmith@testing123.com", "testing123");
INSERT INTO users (first_name,last_name,email,password) VALUES("Michael","Jordan","michael11@testing123.com", "testing123");
INSERT INTO users (first_name,last_name,email,password) VALUES("Poopy","Man","poop@man.testing123.com", "testing123");
INSERT INTO users (first_name,last_name,email,password) VALUES("Test","Test","supertest@testing123.com", "testing123");

DROP TABLE IF EXISTS user_images CASCADE;
CREATE TABLE user_images (

	id VARCHAR(128) NOT NULL,
	user_id INT NOT NULL AUTO_INCREMENT,
	source VARCHAR(64) DEFAULT NULL,
	date_created DATE NOT NULL,
	date_marked_deleted DATE DEFAULT NULL,
	date_deleted DATE DEFAULT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE

) ENGINE=InnoDB CHARSET=utf8;