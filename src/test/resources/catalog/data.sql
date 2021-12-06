INSERT INTO category (code, description, name, short_name)
VALUES('C001', 'Condiments', 'Condiments', 'Condiments');

INSERT INTO category (code, description, name, short_name)
VALUES('C002', 'Beverages', 'Beverages', 'Beverages');

INSERT INTO supplier (address, city, code, company_name, country, phone)
VALUES('ADDRESS 01', 'CITY 01', 'S001', 'SENIOR CHEFS', 'COUNTRY 01', '999999999');

INSERT INTO supplier (address, city, code, company_name, country, phone)
VALUES('ADDRESS 01', 'CITY 01', 'S002', 'PERU UNCLE BOD', 'COUNTRY 01', '999999999');

INSERT INTO product (currency, description, discontinued, name, short_name, unit_price, units_in_stock, category_id, supplier_id)
VALUES('0001', 'Cranberry Sauce', false, 'Northwoods Cranberry Sauce', 'Cranberry Sauce', 40.00, 20, 1, 1);

INSERT INTO product (currency, description, discontinued, name, short_name, unit_price, units_in_stock, category_id, supplier_id)
VALUES('0001', 'Organinc Dried Peara', false, 'Uncle Bob''s Organinc Dried Pears', 'Organinc Dried Peara', 60.00, 20, 1, 1);
