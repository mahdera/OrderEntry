create database db_order_entry;

use db_order_entry;

create table tbl_customer(
	id int auto_increment,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	street varchar(40) not null,
	city varchar(30) not null,
	state varchar(2) not null,
	zip varchar(5) not null,
	country varchar(30) not null,
	primary key(id)
);

create table tbl_product(
	id int auto_increment,
	name varchar(50) not null,
	cost float not null,
	primary key(id)
);

create table tbl_line_item(
	id int auto_increment,
	quantity int not null,
	product_id int not null,
	primary key(id),
	foreign key(product_id) references tbl_product(id)
);

create table tbl_order(
	id int auto_increment,
	total float not null,
	order_date date not null,
	customer_id int not null,
	line_item_id int not null,
	primary key(id),
	foreign key(customer_id) references tbl_customer(id),
	foreign key(line_item_id) references tbl_line_item(id)
);