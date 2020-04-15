from db import ProductDb


def main():
    print("writing product detail")
    db = ProductDb('product.csv', 'aisle.csv', 'department.csv')
    # db.load()
    # print db.getproductcols()
    # for product in db.getproductnames():
    #    print product, '-', db.getaisle(product), '-', db.getdepartment(product)
    db.dumpproductdetail()


if __name__ == "__main__":
    main()