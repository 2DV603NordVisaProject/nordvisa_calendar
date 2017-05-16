import React, { Component } from "react";
class ProvinceSelect extends Component {
  onInputChange(event) {
    console.log("pong");
    console.log(event.target.value)
  }
  render() {
    if (this.props.region === "sweden") {
      return (
        <select>
          <option selected value="" onChange={this.props.onProvinceChange.bind(this)}>Choose Province</option>
          <option value="" onChange={this.props.onProvinceChange.bind(this)}>None</option>
          <option value="blekinge" onChange={this.props.onProvinceChange.bind(this)}>Blekinge</option>
          <option value="bohuslan" onChange={this.props.onProvinceChange.bind(this)}>Bohuslän</option>
          <option value="dalarna" onChange={this.onInputChange}>Dalarna</option>
          <option value="dalsland" onChange={this.props.onProvinceChange.bind(this)}>Dalsland</option>
          <option value="gotland" onChange={this.props.onProvinceChange.bind(this)}>Gotland</option>
          <option value="gastrikland" onChange={this.props.onProvinceChange.bind(this)}>Gästrikland</option>
          <option value="halland" onChange={this.props.onProvinceChange.bind(this)}>Halland</option>
          <option value="halsingland" onChange={this.props.onProvinceChange.bind(this)}>Hälsingland</option>
          <option value="harjedalen" onChange={this.props.onProvinceChange.bind(this)}>Härjedalen</option>
          <option value="jamtland" onChange={this.props.onProvinceChange.bind(this)}>Jämtland</option>
          <option value="lappland" onChange={this.props.onProvinceChange.bind(this)}>Lappland</option>
          <option value="medelpad" onChange={this.props.onProvinceChange.bind(this)}>Medelpad</option>
          <option value="norrbotten" onChange={this.props.onProvinceChange.bind(this)}>Norrbotten</option>
          <option value="narke" onChange={this.props.onProvinceChange.bind(this)}>Närke</option>
          <option value="skane" onChange={this.props.onProvinceChange.bind(this)}>Skåne</option>
          <option value="sodermanland" onChange={this.props.onProvinceChange.bind(this)}>Södermanland</option>
          <option value="uppland" onChange={this.props.onProvinceChange.bind(this)}>Uppland</option>
          <option value="varmland" onChange={this.props.onProvinceChange.bind(this)}>Värmland</option>
          <option value="vasterbotten" onChange={this.props.onProvinceChange.bind(this)}>Västerbotten</option>
          <option value="vastergotland" onChange={this.props.onProvinceChange.bind(this)}>Västergötland</option>
          <option value="vastermanland" onChange={this.props.onProvinceChange.bind(this)}>Västermanland</option>
          <option value="angermanland" onChange={this.props.onProvinceChange.bind(this)}>Ångermanland</option>
          <option value="oland" onChange={this.props.onProvinceChange.bind(this)}>Öland</option>
          <option value="ostergotland" onChange={this.props.onProvinceChange.bind(this)}>Östergötland</option>
        </select>
      );
    } else if(this.props.region === "norway") {
      return (
        <select>
          <option selected value="" onChange={this.props.onProvinceChange.bind(this)}>Choose Province</option>
          <option value="" onChange={this.props.onProvinceChange.bind(this)}>None</option>
          <option value="agder" onChange={this.props.onProvinceChange.bind(this)}>Agder</option>
          <option value="follo" onChange={this.props.onProvinceChange.bind(this)}>Follo</option>
          <option value="gudbrandsdalen" onChange={this.props.onProvinceChange.bind(this)}>Gudbrandsdalen</option>
          <option value="hadeland" onChange={this.props.onProvinceChange.bind(this)}>Hadeland</option>
          <option value="haugalandet" onChange={this.props.onProvinceChange.bind(this)}>Haugalandet</option>
          <option value="hedmarken" onChange={this.props.onProvinceChange.bind(this)}>Hedmarken</option>
          <option value="helgeland" onChange={this.props.onProvinceChange.bind(this)}>Helgeland</option>
          <option value="hordaland" onChange={this.props.onProvinceChange.bind(this)}>Hordaland</option>
          <option value="halogaland" onChange={this.props.onProvinceChange.bind(this)}>Hålogaland</option>
          <option value="jaeren" onChange={this.props.onProvinceChange.bind(this)}>Jæren</option>
          <option value="lofoten" onChange={this.props.onProvinceChange.bind(this)}>Lofoten</option>
          <option value="namdalen" onChange={this.props.onProvinceChange.bind(this)}>Namdalen</option>
          <option value="nordland" onChange={this.props.onProvinceChange.bind(this)}>Nordland</option>
          <option value="nordmore" onChange={this.props.onProvinceChange.bind(this)}>Nordmøre</option>
          <option value="ofoten" onChange={this.props.onProvinceChange.bind(this)}>Ofoten</option>
          <option value="romerike" onChange={this.props.onProvinceChange.bind(this)}>Romerike</option>
          <option value="romsdal" onChange={this.props.onProvinceChange.bind(this)}>Romsdal</option>
          <option value="ryfylke" onChange={this.props.onProvinceChange.bind(this)}>Ryfylke</option>
          <option value="salten" onChange={this.props.onProvinceChange.bind(this)}>Salten</option>
          <option value="solor" onChange={this.props.onProvinceChange.bind(this)}>Solør</option>
          <option value="sunnmore" onChange={this.props.onProvinceChange.bind(this)}>Sunnmøre</option>
          <option value="toten" onChange={this.props.onProvinceChange.bind(this)}>Toten</option>
          <option value="tronedelag" onChange={this.props.onProvinceChange.bind(this)}>Trøndelag</option>
          <option value="vingulmark" onChange={this.props.onProvinceChange.bind(this)}>Vingulmark</option>
          <option value="valdres" onChange={this.props.onProvinceChange.bind(this)}>Valdres</option>
          <option value="vesteralen" onChange={this.props.onProvinceChange.bind(this)}>Vesterålen</option>
          <option value="osterdalen" onChange={this.props.onProvinceChange.bind(this)}>Østerdalen</option>
        </select>
      );
    } else if (this.props.region === "iceland") {
      return (
        <select>
          <option selected value="" onChange={this.props.onProvinceChange.bind(this)}>Choose Province</option>
          <option value="" onChange={this.props.onProvinceChange.bind(this)}>None</option>
          <option value="arnessysla" onChange={this.props.onProvinceChange.bind(this)}>Árnessýsla</option>
          <option value="austurbaroastrandarsysla" onChange={this.props.onProvinceChange.bind(this)}>Austur-Barðastrandarsýsla</option>
          <option value="austurhunavatnssysla" onChange={this.props.onProvinceChange.bind(this)}>Austur-Húnavatnssýsla</option>
          <option value="austurskaftafellssysla" onChange={this.props.onProvinceChange.bind(this)}>Austur-Skaftafellssýsla</option>
          <option value="borgarfjaroarsysla" onChange={this.props.onProvinceChange.bind(this)}>Borgarfjarðarsýsla</option>
          <option value="dalasysla" onChange={this.props.onProvinceChange.bind(this)}>Dalasýsla</option>
          <option value="eyjafjaroarsysla" onChange={this.props.onProvinceChange.bind(this)}>Eyjafjarðarsýsla</option>
          <option value="gullbringusysla" onChange={this.props.onProvinceChange.bind(this)}>Gullbringusýsla</option>
          <option value="kjosarsysla" onChange={this.props.onProvinceChange.bind(this)}>Kjósarsýsla</option>
          <option value="myrasysla" onChange={this.props.onProvinceChange.bind(this)}>Mýrasýsla</option>
          <option value="norourisafjaroarsysla" onChange={this.props.onProvinceChange.bind(this)}>Norður-Ísafjarðarsýsla</option>
          <option value="norourmulasysla" onChange={this.props.onProvinceChange.bind(this)}>Norður-Múlasýsla</option>
          <option value="norourpingeyjarsysla" onChange={this.props.onProvinceChange.bind(this)}>Norður-Þingeyjarsýsla</option>
          <option value="rangarvallasysla" onChange={this.props.onProvinceChange.bind(this)}>Rangárvallasýsla</option>
          <option value="skagafjaroarsysla" onChange={this.props.onProvinceChange.bind(this)}>Skagafjarðarsýsla</option>
          <option value="sneafellsnesoghnappadalssysla" onChange={this.props.onProvinceChange.bind(this)}>Snæfellsnes-og Hnappadalssýsla</option>
          <option value="strandasysla" onChange={this.props.onProvinceChange.bind(this)}>Strandasýsla</option>
          <option value="suourmulasysla" onChange={this.props.onProvinceChange.bind(this)}>Suður-Múlasýsla</option>
          <option value="suourpingeyjarsysla" onChange={this.props.onProvinceChange.bind(this)}>Suður-Þingeyjarsýsla</option>
          <option value="vesturbaroastrandarsysla" onChange={this.props.onProvinceChange.bind(this)}>Vestur-Barðastrandarsýsla</option>
          <option value="vesturhunavatnssysla" onChange={this.props.onProvinceChange.bind(this)}>Vestur-Húnavatnssýsla</option>
          <option value="vesturisafjaroarsysla" onChange={this.props.onProvinceChange.bind(this)}>Vestur-Ísafjarðarsýsla</option>
          <option value="vesturskaftafellssysla" onChange={this.props.onProvinceChange.bind(this)}>Vestur-Skaftafellssýsla</option>
        </select>
      );
    } else if (this.props.region === "denmark") {
      return (
        <select>
          <option selected value="" onChange={this.props.onProvinceChange.bind(this)}>Choose Province</option>
          <option value="" onChange={this.props.onProvinceChange.bind(this)}>None</option>
          <option value="nordjylland" onChange={this.props.onProvinceChange.bind(this)}>Nordjylland</option>
          <option value="midtjylland" onChange={this.props.onProvinceChange.bind(this)}>Midtjylland</option>
          <option value="syddanmark" onChange={this.props.onProvinceChange.bind(this)}>Syddanmark</option>
          <option value="hovedstaden" onChange={this.props.onProvinceChange.bind(this)}>Hovedstaden</option>
          <option value="sjaelland" onChange={this.props.onProvinceChange.bind(this)}>Sjælland</option>
        </select>
      );
    } else {
      return (
        <div></div>
      );
    }
  }
}
export default ProvinceSelect;
