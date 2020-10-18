Run these as user postgres:
	CREATE USER profectus WITH PASSWORD 'profectus';
	CREATE DATABASE profectusdb WITH OWNER profectus;
	
Connect to profectusdb as user profectus and run the followings:

CREATE TABLE	categories(
  cat_id	SERIAL,
  category	VARCHAR(50) NOT NULL,
  CONSTRAINT categories_pk PRIMARY KEY(cat_id)
);
  
CREATE TABLE	products(
  prod_code		VARCHAR(50),
  prod_name		VARCHAR(50) NOT NULL,
  purch_price	DECIMAL(7,2) DEFAULT 0,
  cat_id		INT,
  CONSTRAINT product_pk PRIMARY KEY(prod_code),
  CONSTRAINT product_fk FOREIGN KEY(cat_id) REFERENCES categories(cat_id)
);

CREATE TABLE	purchases(
  purch_id		SERIAL,
  prod_code		VARCHAR(50),
  purch_date 	DATE,
  purch_qty		INT DEFAULT 0,
  purch_price	DECIMAL(7,2),
  CONSTRAINT purchases_pk PRIMARY KEY(purch_id),
  CONSTRAINT purchases_fk FOREIGN KEY(prod_code) REFERENCES products(prod_code)
);

CREATE TABLE	sales(
  sales_id		SERIAL,
  prod_code		VARCHAR(50),
  sales_date 	DATE,
  sales_qty		INT DEFAULT 0,
  sales_price	DECIMAL(7,2),
  CONSTRAINT sales_pk PRIMARY KEY(sales_id),
  CONSTRAINT sales_fk FOREIGN KEY(prod_code) REFERENCES products(prod_code)
);
