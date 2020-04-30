create sequence hibernate_sequence start 1 increment 1
create table address (id int8 not null, country varchar(255) not null, created_on timestamp, line1 varchar(255) not null, line2 varchar(255), line3 varchar(255), postal_code varchar(255) not null, version int8 not null, primary key (id))
create table bank_account (id int8 not null, account_holder varchar(255), account_holder_type varchar(255), account_number varchar(255), bank_name varchar(255), country varchar(255), created_on timestamp, currency varchar(255), iban varchar(255), last_updated_on timestamp, version int8 not null, primary key (id))
create table customer (id int8 not null, created_on timestamp, email varchar(255), first_name varchar(255), last_name varchar(255), last_updated_on timestamp, phone varchar(255), version int8 not null, address_id int8, primary key (id))
create table delivery (id int8 not null, delivered_on timestamp, delivery_scheduled_on timestamp, customer_id int8, purchase_id int8, primary key (id))
create table fetch_user (id int8 not null, created_on timestamp, last_updated_on timestamp, password_hash varchar(255), username varchar(255), version int8 not null, primary key (id))
create table merchant (id int8 not null, contact_name varchar(255), created_on timestamp, currency varchar(255), email varchar(255), last_updated_on timestamp, locale varchar(255), name varchar(255), phone varchar(255), version int8 not null, address_id int8 not null, bank_account_id int8, primary key (id))
create table postal_code (id int8 not null, code varchar(255), lat float8 not null, lon float8 not null, primary key (id))
create table product (id int8 not null, created_on timestamp, description varchar(255), in_stock boolean not null, last_updated_on timestamp, merchant_id int8 not null, name varchar(255), price float8, product_catalog_id int8 not null, unit varchar(255), version int8 not null, primary key (id))
create table product_catalog (id int8 not null, level1 varchar(255), level2 varchar(255), level3 varchar(255), primary key (id))
create table purchase (id int8 not null, created_on timestamp, primary key (id))
create table purchase_items (purchase_id int8 not null, items_id int8 not null)
create table purchase_item (id int8 not null, qty int8, product_id int8, purchase_id int8, primary key (id))
alter table customer add constraint UK_dwk6cx0afu8bs9o4t536v1j5v unique (email)
alter table fetch_user add constraint UK_tmwg1cdcmlqyg7gjwjmkx5gis unique (username)
alter table merchant add constraint UK_hpejy4i1nk4u3251c6ba2pmpv unique (name)
alter table postal_code add constraint UK_kxaqs1uvf03c1vrc32yr0fuhq unique (code)
alter table product_catalog add constraint UKarj8qqxfegerk08iu9chtt82y unique (level1, level2, level3)
alter table purchase_items add constraint UK_k6ghloc692e9a711brl893efh unique (items_id)
alter table customer add constraint FKglkhkmh2vyn790ijs6hiqqpi foreign key (address_id) references address
alter table delivery add constraint FKr0mg2e4p18frsju6qut84g8fs foreign key (customer_id) references customer
alter table delivery add constraint FKelrpm4ntwobywlwjcthussy2l foreign key (purchase_id) references purchase
alter table merchant add constraint FKfo02f19g2qny3663iqsh6qdxo foreign key (address_id) references address
alter table merchant add constraint FKqwbwlss1piq3n13e91x6i9lid foreign key (bank_account_id) references bank_account
alter table product add constraint FKk47qmalv2pg906heielteubu7 foreign key (merchant_id) references merchant
alter table product add constraint FKfahaqluqrekfatrms315e7m1w foreign key (product_catalog_id) references product_catalog
alter table purchase_items add constraint FK606voercdyoieorwxu0ux22xw foreign key (items_id) references purchase_item
alter table purchase_items add constraint FKfbch0rs5h1ih9ng5tn5ydi7vm foreign key (purchase_id) references purchase
alter table purchase_item add constraint FKq69apam78dbi0cggl37ge6auf foreign key (product_id) references product
alter table purchase_item add constraint FK1mncc5yaore1sibgpj3jc4a7u foreign key (purchase_id) references purchase
