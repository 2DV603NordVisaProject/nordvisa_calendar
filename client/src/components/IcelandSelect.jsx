import React from 'react';
import PropTypes from 'prop-types';

const IcelandSelect = (props, context) => {
  const language = context.language.WidgetView;

  return (
    <div className={props.showProvince ? '' : 'hidden'}>
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
    </div>
  );
};

IcelandSelect.contextTypes = {
  language: PropTypes.object,
};

IcelandSelect.defaultProps = {
  showProvince: false,
};

IcelandSelect.propTypes = {
  showProvince: PropTypes.boolean,
  onChange: PropTypes.func.isRequired,
};
