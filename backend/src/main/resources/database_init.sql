-- Table: users
CREATE TABLE IF NOT EXISTS users (
    userID BIGINT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(255) NOT NULL UNIQUE,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    emailAddress VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    bio TEXT,
    userType VARCHAR(50),
    profileImage BLOB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: recipes
CREATE TABLE IF NOT EXISTS recipes (
    recipeID BIGINT AUTO_INCREMENT PRIMARY KEY,
    creatorID BIGINT,
    name VARCHAR(255) NOT NULL,
    ingredients TEXT,
    description TEXT,
    cookingTime INT,
    directions TEXT, -- New field
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    tags TEXT,
    images BLOB,
    FOREIGN KEY (creatorID) REFERENCES users(userID) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: tags
CREATE TABLE IF NOT EXISTS tags (
    tagID BIGINT AUTO_INCREMENT PRIMARY KEY,
    tagName VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: categories
CREATE TABLE IF NOT EXISTS categories (
    categoryID BIGINT AUTO_INCREMENT PRIMARY KEY,
    categoryName VARCHAR(255) NOT NULL UNIQUE,
    tags TEXT, -- Comma-separated list of tag names associated with the category
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: notifications
CREATE TABLE IF NOT EXISTS notifications (
    notificationID BIGINT AUTO_INCREMENT PRIMARY KEY,
    receiverID BIGINT,
    body TEXT,
    title VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (receiverID) REFERENCES users(userID) ON DELETE CASCADE
);

-- Table: Followers (assuming this is for user follow relationships)
CREATE TABLE IF NOT EXISTS followers (
    user_id BIGINT,
    follower_id BIGINT,
    PRIMARY KEY (user_id, follower_id),
    FOREIGN KEY (user_id) REFERENCES users(userID) ON DELETE CASCADE,
    FOREIGN KEY (follower_id) REFERENCES users(userID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews (
    reviewID BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipeID BIGINT NOT NULL,
    description TEXT,
    author VARCHAR(255),
    rating INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (recipeID) REFERENCES recipes(recipeID) ON DELETE CASCADE
);