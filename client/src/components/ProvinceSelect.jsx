import React from 'react';
import PropTypes from 'prop-types';

const ProvinceSelect = (props, context) => {
  const language = context.language.WidgetView;

  if (this.props.region === 'sweden') {
    return (
      <select defaultValue="" name="province" onChange={props.onChange} className="capitalize">
        <option value="" className="capitalize">{language.chooseProvince}</option>
        <option value="" className="capitalize">{language.none}</option>
        <option value="blekinge">Blekinge</option>
        <option value="bohuslan">Bohuslän</option>
        <option value="dalarna">Dalarna</option>
        <option value="dalsland">Dalsland</option>
        <option value="gotland">Gotland</option>
        <option value="gastrikland">Gästrikland</option>
        <option value="halland">Halland</option>
        <option value="halsingland">Hälsingland</option>
        <option value="harjedalen">Härjedalen</option>
        <option value="jamtland">Jämtland</option>
        <option value="lappland">Lappland</option>
        <option value="medelpad">Medelpad</option>
        <option value="norrbotten">Norrbotten</option>
        <option value="narke">Närke</option>
        <option value="skane">Skåne</option>
        <option value="sodermanland">Södermanland</option>
        <option value="uppland">Uppland</option>
        <option value="varmland">Värmland</option>
        <option value="vasterbotten">Västerbotten</option>
        <option value="vastergotland">Västergötland</option>
        <option value="vastermanland">Västermanland</option>
        <option value="angermanland">Ångermanland</option>
        <option value="oland">Öland</option>
        <option value="ostergotland">Östergötland</option>
      </select>
    );
  } else if (this.props.region === 'norway') {
    return (
      <select name="province" defaultValue="" onChange={props.onChange} className="capitalize">
        <option value="" className="capitalize">{language.chooseProvince}</option>
        <option value="" className="capitalize">{language.none}</option>
        <option value="agder">Agder</option>
        <option value="follo">Follo</option>
        <option value="gudbrandsdalen">Gudbrandsdalen</option>
        <option value="hadeland">Hadeland</option>
        <option value="haugalandet">Haugalandet</option>
        <option value="hedmarken">Hedmarken</option>
        <option value="helgeland">Helgeland</option>
        <option value="hordaland">Hordaland</option>
        <option value="halogaland">Hålogaland</option>
        <option value="jaeren">Jæren</option>
        <option value="lofoten">Lofoten</option>
        <option value="namdalen">Namdalen</option>
        <option value="nordland">Nordland</option>
        <option value="nordmore">Nordmøre</option>
        <option value="ofoten">Ofoten</option>
        <option value="romerike">Romerike</option>
        <option value="romsdal">Romsdal</option>
        <option value="ryfylke">Ryfylke</option>
        <option value="salten">Salten</option>
        <option value="solor">Solør</option>
        <option value="sunnmore">Sunnmøre</option>
        <option value="toten">Toten</option>
        <option value="tronedelag">Trøndelag</option>
        <option value="vingulmark">Vingulmark</option>
        <option value="valdres">Valdres</option>
        <option value="vesteralen">Vesterålen</option>
        <option value="osterdalen">Østerdalen</option>
      </select>
    );
  } else if (this.props.region === 'iceland') {
    return (
      <select name="province" defaultValue="" onChange={props.onChange} className="capitalize">
        <option value="" className="capitalize">{language.chooseProvince}</option>
        <option value="" className="capitalize">{language.none}</option>
        <option value="arnessysla">Árnessýsla</option>
        <option value="austurbaroastrandarsysla">Austur-Barðastrandarsýsla</option>
        <option value="austurhunavatnssysla">Austur-Húnavatnssýsla</option>
        <option value="austurskaftafellssysla">Austur-Skaftafellssýsla</option>
        <option value="borgarfjaroarsysla">Borgarfjarðarsýsla</option>
        <option value="dalasysla">Dalasýsla</option>
        <option value="eyjafjaroarsysla">Eyjafjarðarsýsla</option>
        <option value="gullbringusysla">Gullbringusýsla</option>
        <option value="kjosarsysla">Kjósarsýsla</option>
        <option value="myrasysla">Mýrasýsla</option>
        <option value="norourisafjaroarsysla">Norður-Ísafjarðarsýsla</option>
        <option value="norourmulasysla">Norður-Múlasýsla</option>
        <option value="norourpingeyjarsysla">Norður-Þingeyjarsýsla</option>
        <option value="rangarvallasysla">Rangárvallasýsla</option>
        <option value="skagafjaroarsysla">Skagafjarðarsýsla</option>
        <option value="sneafellsnesoghnappadalssysla">Snæfellsnes-og Hnappadalssýsla</option>
        <option value="strandasysla">Strandasýsla</option>
        <option value="suourmulasysla">Suður-Múlasýsla</option>
        <option value="suourpingeyjarsysla">Suður-Þingeyjarsýsla</option>
        <option value="vesturbaroastrandarsysla">Vestur-Barðastrandarsýsla</option>
        <option value="vesturhunavatnssysla">Vestur-Húnavatnssýsla</option>
        <option value="vesturisafjaroarsysla">Vestur-Ísafjarðarsýsla</option>
        <option value="vesturskaftafellssysla">Vestur-Skaftafellssýsla</option>
      </select>
    );
  } else if (this.props.region === 'denmark') {
    return (
      <select defaultValue="" name="province" onChange={props.onChange} className="capitalize">
        <option value="" className="capitalize">{language.chooseProvince}</option>
        <option value="" className="capitalize">{language.none}</option>
        <option value="nordjylland">Nordjylland</option>
        <option value="midtjylland">Midtjylland</option>
        <option value="syddanmark">Syddanmark</option>
        <option value="hovedstaden">Hovedstaden</option>
        <option value="sjaelland">Sjælland</option>
      </select>
    );
  }
  return (
    <div />
  );
};

ProvinceSelect.contextTypes = {
  language: PropTypes.object,
};

ProvinceSelect.propTypes = {
  onChange: PropTypes.func.isRequired,
};

export default ProvinceSelect;
