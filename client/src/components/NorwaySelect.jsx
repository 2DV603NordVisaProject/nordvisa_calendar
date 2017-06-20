import React from 'react';
import PropTypes from 'prop-types';

const NorwaySelect = (props, context) => {
  const language = context.language.WidgetView;

  return (
    <div className={props.showProvince ? '' : 'hidden'}>
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
    </div>
  );
};

NorwaySelect.contextTypes = {
  language: PropTypes.object,
};

NorwaySelect.defaultProps = {
  showProvince: false,
};

NorwaySelect.propTypes = {
  showProvince: PropTypes.boolean,
  onChange: PropTypes.func.isRequired,
};
