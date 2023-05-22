package com.example.lenta_storage_app.api

import com.example.lenta_storage_app.model.entities.Client
import com.example.lenta_storage_app.model.entities.Complectation
import com.example.lenta_storage_app.model.entities.Employee
import com.example.lenta_storage_app.model.entities.Income
import com.example.lenta_storage_app.model.entities.IncomeOrder
import com.example.lenta_storage_app.model.entities.IncomeStat
import com.example.lenta_storage_app.model.entities.Product
import com.example.lenta_storage_app.model.entities.Shipment
import com.example.lenta_storage_app.model.entities.ShipmentOrder
import com.example.lenta_storage_app.model.entities.ShipmentStat
import com.example.lenta_storage_app.model.entities.Statistic
import com.example.lenta_storage_app.model.entities.Storage
import com.example.lenta_storage_app.model.entities.Supplier
import com.example.lenta_storage_app.model.entities.User
import com.example.lenta_storage_app.model.tables.Clients
import com.example.lenta_storage_app.model.tables.Complectations
import com.example.lenta_storage_app.model.tables.Employees
import com.example.lenta_storage_app.model.tables.IncomeOrders
import com.example.lenta_storage_app.model.tables.Incomes
import com.example.lenta_storage_app.model.tables.Products
import com.example.lenta_storage_app.model.tables.ShipmentOrders
import com.example.lenta_storage_app.model.tables.Shipments
import com.example.lenta_storage_app.model.tables.Storages
import com.example.lenta_storage_app.model.tables.Suppliers
import com.example.lenta_storage_app.model.tables.Users
import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.avg
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.groupBy
import org.ktorm.dsl.gt
import org.ktorm.dsl.insert
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.map
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import org.ktorm.dsl.sum
import org.ktorm.dsl.where
import org.ktorm.expression.SqlExpression
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

class ApplicationDbContext {
    private var api = Api()

    fun getStorages() : ArrayList<Storage> {
        val db = api.getConnection()
        val resultList = ArrayList<Storage>()

        for (row in db.from(Storages).select()
            .orderBy(Storages.orderingNumber.asc())) {
            val item = Storage(
                row[Storages.id]!!.toInt(),
                row[Storages.name].toString(),
                row[Storages.storageConditions].toString(),
                row[Storages.orderingNumber]!!.toInt()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getProducts(storageId : Int) : ArrayList<Product> {
        val db = api.getConnection()
        val resultList = ArrayList<Product>()

        for (row in db.from(Products).select()
            .where { (Products.storageId eq storageId) }) {
            val item = Product(
                row[Products.id]!!.toInt(),
                row[Products.name].toString(),
                row[Products.storageId]!!.toInt(),
                row[Products.inStock]!!.toInt()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getProducts() : ArrayList<Product> {
        val db = api.getConnection()
        val resultList = ArrayList<Product>()

        for (row in db.from(Products).select()) {
            val item = Product(
                row[Products.id]!!.toInt(),
                row[Products.name].toString(),
                row[Products.storageId]!!.toInt(),
                row[Products.inStock]!!.toInt()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun addProduct(name : String, storageId: Int) {
        val db = api.getConnection()
        db.insert(Products) {
            set(it.name, name)
            set(it.inStock, 0)
            set(it.storageId, storageId)
        }
    }

    fun deleteProduct(id : Int) {
        val db = api.getConnection()
        db.delete(Products) { it.id eq id }
    }

    fun getStatisticsOnDate(date : LocalDate) : ArrayList<Statistic> {
        val db = api.getJdbcConnection()
        var resultList = ArrayList<Statistic>()
        val convertedDate = java.sql.Date.valueOf(date.toString())

        try {
            val sql = "call lenta_storage.get_stats_on_date('$convertedDate');".trimIndent()
            val query = db.prepareStatement(sql)
            val result = query.executeQuery()
            while (result.next()) {
                resultList.add(Statistic(
                    result.getString(1),
                    result.getInt(2),
                    result.getInt(3),
                    result.getInt(4)
                ))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return resultList
    }

    fun getShipmentStats(date : LocalDate) : ArrayList<ShipmentStat> {
        val db = api.getConnection()
        val resultList = ArrayList<ShipmentStat>()
        val sumAmount = avg(Shipments.productAmount).aliased("sum_amount")

        val convertedDate = java.sql.Date.valueOf(date.toString())
        for (row in db.from(Shipments).select(Shipments.shipmentDate, sumAmount)
            .where { (Shipments.shipmentDate eq convertedDate) }
            .groupBy(Shipments.shipmentDate)) {
            val item = ShipmentStat(
                LocalDate.parse(row[Shipments.shipmentDate].toString()),
                row.getInt(2)
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getIncomes(incomeOrderId : Int) : ArrayList<Income> {
        val db = api.getConnection()
        val resultList = ArrayList<Income>()

        for (row in db.from(Incomes).select()
            .where { Incomes.incomeOrderId eq incomeOrderId }) {
            val item = Income(
                row[Incomes.id]!!.toInt(),
                LocalDate.parse(row[Incomes.incomeDate].toString()),
                LocalTime.parse(row[Incomes.incomeTime].toString()),
                row[Incomes.productAmount]!!.toInt(),
                row[Incomes.productId]!!.toInt(),
                row[Incomes.incomeOrderId]!!.toInt(),
                row[Incomes.empId]!!.toInt()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getIncomes() : ArrayList<Income> {
        val db = api.getConnection()
        val resultList = ArrayList<Income>()

        for (row in db.from(Incomes).select()) {
            val item = Income(
                row[Incomes.id]!!.toInt(),
                LocalDate.parse(row[Incomes.incomeDate].toString()),
                LocalTime.parse(row[Incomes.incomeTime].toString()),
                row[Incomes.productAmount]!!.toInt(),
                row[Incomes.productId]!!.toInt(),
                row[Incomes.incomeOrderId]!!.toInt(),
                row[Incomes.empId]!!.toInt()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun addIncome(incomeDate : LocalDate, incomeTime : LocalTime, productAmount : Int,
                  productId : Int, incomeOrderId : Int, empId : Int) {
        val db = api.getConnection()
        db.insert(Incomes) {
            set(it.incomeDate, java.sql.Date.valueOf(incomeDate.toString()))
            set(it.incomeTime, java.sql.Time.valueOf(incomeTime.toString()))
            set(it.productAmount, productAmount)
            set(it.productId, productId)
            set(it.incomeOrderId, incomeOrderId)
            set(it.empId, empId)
        }
    }

    fun deleteIncome(id : Int) {
        val db = api.getConnection()
        db.delete(Incomes) { it.id eq id }
    }

    fun getEmployees() : ArrayList<Employee> {
        val db = api.getConnection()
        val resultList = ArrayList<Employee>()

        for (row in db.from(Employees).select()) {
            val item = Employee(
                row[Employees.id]!!.toInt(),
                row[Employees.surname].toString(),
                row[Employees.firstname].toString(),
                row[Employees.patronymic].toString(),
                row[Employees.positionId]!!.toInt(),
                row[Employees.departmentId]!!.toInt(),
                row[Employees.userId]!!.toInt()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getIncomeOrders() : ArrayList<IncomeOrder> {
        val db = api.getConnection()
        val resultList = ArrayList<IncomeOrder>()

        for (row in db.from(IncomeOrders).select()) {
            val item = IncomeOrder(
                row[IncomeOrders.id]!!.toInt(),
                row[IncomeOrders.orderNumber]!!.toInt(),
                LocalDate.parse(row[IncomeOrders.creationDate].toString()),
                row[IncomeOrders.supplierId]!!.toInt()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun addIncomeOrder(orderNumber  : Int, creationDate : LocalDate, supplierId : Int) {
        val db = api.getConnection()
        db.insert(IncomeOrders) {
            set(it.orderNumber, orderNumber)
            set(it.creationDate, java.sql.Date.valueOf(creationDate.toString()))
            set(it.supplierId, supplierId)
        }
    }

    fun deleteIncomeOrder(id : Int) {
        val db = api.getConnection()
        db.delete(IncomeOrders) { it.id eq id }
    }

    fun getShipments(shipmentOrderId: Int) : ArrayList<Shipment> {
        val db = api.getConnection()
        val resultList = ArrayList<Shipment>()

        for (row in db.from(Shipments).select()
            .where { Shipments.shipmentOrderId eq shipmentOrderId }) {
            val item = Shipment(
                row[Shipments.id]!!.toInt(),
                LocalDate.parse(row[Shipments.shipmentDate].toString()),
                LocalTime.parse(row[Shipments.shipmentTime].toString()),
                row[Shipments.productAmount]!!.toInt(),
                row[Shipments.productId]!!.toInt(),
                row[Shipments.shipmentOrderId]!!.toInt(),
                row[Shipments.empId]!!.toInt()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun addShipment(shipmentDate : LocalDate, shipmentTime : LocalTime, productAmount : Int,
                    productId : Int, shipmentOrderId : Int, empId : Int) {
        val db = api.getConnection()
        db.insert(Shipments) {
            set(it.shipmentDate, java.sql.Date.valueOf(shipmentDate.toString()))
            set(it.shipmentTime, java.sql.Time.valueOf(shipmentTime.toString()))
            set(it.productAmount, productAmount)
            set(it.productId, productId)
            set(it.shipmentOrderId, shipmentOrderId)
            set(it.empId, empId)
        }
    }

    fun deleteShipment(id : Int) {
        val db = api.getConnection()
        db.delete(Shipments) { it.id eq id }
    }

    fun getShipmentOrders() : ArrayList<ShipmentOrder> {
        val db = api.getConnection()
        val resultList = ArrayList<ShipmentOrder>()

        for (row in db.from(ShipmentOrders).select()) {
            val item = ShipmentOrder(
                row[ShipmentOrders.id]!!.toInt(),
                row[ShipmentOrders.orderNumber]!!.toInt(),
                LocalDate.parse(row[ShipmentOrders.creationDate].toString()),
                row[ShipmentOrders.clientId]!!.toInt()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun addShipmentOrder(orderNumber  : Int, creationDate : LocalDate, clientId : Int) {
        val db = api.getConnection()
        db.insert(ShipmentOrders) {
            set(it.orderNumber, orderNumber)
            set(it.creationDate, java.sql.Date.valueOf(creationDate.toString()))
            set(it.clientId, clientId)
        }
    }

    fun deleteShipmentOrder(id : Int) {
        val db = api.getConnection()
        db.delete(ShipmentOrders) { it.id eq id }
    }

    fun getSuppliers() : ArrayList<Supplier> {
        val db = api.getConnection()
        val resultList = ArrayList<Supplier>()

        for (row in db.from(Suppliers).select()) {
            val item = Supplier(
                row[Suppliers.id]!!.toInt(),
                row[Suppliers.name].toString()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getClients() : ArrayList<Client> {
        val db = api.getConnection()
        val resultList = ArrayList<Client>()

        for (row in db.from(Clients).select()) {
            val item = Client(
                row[Clients.id]!!.toInt(),
                row[Clients.name].toString()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getUser(username : String) : User? {
        val db = api.getConnection()
        val resultList = ArrayList<User>()

        for (row in db.from(Users).select()
            .where { Users.userName eq username }) {
            val item = User(
                row[Users.id]!!.toInt(),
                row[Users.userName].toString(),
                row[Users.password].toString(),
                row[Users.roleId]!!.toInt()
            )
            resultList.add(item)
        }

        if (resultList.isEmpty())
        {
            return null
        }

        return resultList[0]
    }

    fun getComplectations() : ArrayList<Complectation> {
        val db = api.getConnection()
        val resultList = ArrayList<Complectation>()

        for (row in db.from(Complectations).select()) {
            val item = Complectation(
                row[Complectations.id]!!.toInt(),
                row[Complectations.incomeId]!!.toInt(),
                row[Complectations.productAmount]!!.toInt(),
                LocalDate.parse(row[Complectations.complectationDate].toString()),
                LocalTime.parse(row[Complectations.complectationTime].toString())
            )
            resultList.add(item)
        }

        return resultList
    }

    fun addComplectation(incomeId : Int, productAmount: Int, complectationDate : LocalDate,
                         complectationTime : LocalTime) {
        val db = api.getConnection()
        db.insert(Complectations) {
            set(it.incomeId, incomeId)
            set(it.productAmount, productAmount)
            set(it.complectationDate, java.sql.Date.valueOf(complectationDate.toString()))
            set(it.complectationTime, java.sql.Time.valueOf(complectationTime.toString()))
        }
    }

    fun deleteComplectation(id : Int) {
        val db = api.getConnection()
        db.delete(Complectations) { it.id eq id }
    }
}