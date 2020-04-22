import pandas as pd
import csv

class ProductDb:

    def __init__(self, productcsv, aislecsv, departmentcsv):
        self.productcsv = productcsv
        self.aislecsv = aislecsv
        self.departmentcsv = departmentcsv
        self.productdb = None
        self.aisledb = None
        self.deparmentdb = None

    def load(self):
        self.productdb = pd.read_csv(self.productcsv)
        self.aisledb = pd.read_csv(self.aislecsv)
        self.deparmentdb = pd.read_csv(self.departmentcsv)

    def getproductnames(self):
        return self.productdb['product_name'].tolist()

    def getaislenames(self):
        return self.aisledb['aisle'].tolist()

    def getdepartmentnames(self):
        return self.deparmentdb['department'].tolist()

    def getproductcols(self):
        return self.productdb.columns

    def getdepartment(self, productname):
        departmentid = self.productdb.loc[self.productdb['product_name'] == productname, 'department_id'].iloc[0]
        deparmentname = self.deparmentdb.loc[self.deparmentdb['department_id'] == departmentid, 'department'].iloc[0]
        return deparmentname

    def getaisle(self, productname):
        aisleid = self.productdb.loc[self.productdb['product_name'] == productname, 'aisle_id'].iloc[0]
        aislename = self.aisledb.loc[self.aisledb['aisle_id'] == aisleid, 'aisle'].iloc[0]
        return aislename

    def dumpproductdetail(self):
        self.load()
        with open('../platform-admin/product_detail.csv', mode='wb') as f:
            writer = csv.writer(f, delimiter="|")
            for product in self.getproductnames():
                writer.writerow([product, self.getaisle(product), self.getdepartment(product)])



