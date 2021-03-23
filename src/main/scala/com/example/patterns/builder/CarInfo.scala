package com.example.patterns.builder

import com.example.patterns.builder.CarInfo.Manufacturer

import java.util.Date

sealed trait CarInfo

object CarInfo {
  sealed trait Empty extends CarInfo
  sealed trait Name extends CarInfo
  sealed trait Manufacturer extends CarInfo
  sealed trait Model extends CarInfo
  sealed trait SerialNumber extends CarInfo
  sealed trait License extends CarInfo
  sealed trait LicenseDate extends CarInfo
  sealed trait Extras extends CarInfo
  sealed trait CurrentKm extends CarInfo

  type MandatoryInfo = Empty with Name with Manufacturer with Model with SerialNumber with License with LicenseDate with Extras with CurrentKm
}

case class MyCarPojoBuilder[I <: CarInfo](
                                         name: String = "", manufacturer: String = "", model: String = "",
                                         serialNumber: String = "", license: String = "", licenseDate: Date = new Date(),
                                         extras: List[String] = List.empty, currentKm: Int = 0,
                                         carStandName: String = "N/A", sellerName: String = "N/A"
                                         ) {

  def withName(name: String): MyCarPojoBuilder[I with CarInfo.Name] = this.copy(name = name)
  def withManufacturer(manufacturer: String): MyCarPojoBuilder[I with CarInfo.Manufacturer] = this.copy(manufacturer = manufacturer)
  def withModel(model: String): MyCarPojoBuilder[I with CarInfo.Model] = this.copy(model = model)
  def withSerialNumber(serialNumber: String): MyCarPojoBuilder[I with CarInfo] = this.copy(serialNumber = serialNumber)
  def withLicense(license: String): MyCarPojoBuilder[I with CarInfo] = this.copy(license = license)
  def withLicenseDate(licenseDate: Date): MyCarPojoBuilder[I with CarInfo] = this.copy(licenseDate = licenseDate)
  def withExtras(extras: List[String]): MyCarPojoBuilder[I with CarInfo] = this.copy(extras = extras)
  def withCurrentKm(currentKm: Int): MyCarPojoBuilder[I with CarInfo] = this.copy(currentKm = currentKm)
}

case class MyCarPojo(name: String, manufacturer: String, model: String,
                     serialNumber: String, license: String, licenseDate: Date,
                     extras: List[String], currentKm: Int,
                     carStandName: String, sellerName: String)