import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  showProvince: PropTypes.boolean,
  onChange: PropTypes.func.isRequired,
};

const defaultProps = {
  showProvince: false,
};

const contextTypes = {
  language: PropTypes.object,
};

const SwedenSelect = ({ onChange, showProvince }, context) => {
  const language = context.language.WidgetView;

  return (
    <div className={showProvince ? '' : 'hidden'}>
      <select defaultValue="" name="province" onChange={onChange} className="capitalize">
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
    </div>
  );
};

SwedenSelect.propTypes = propTypes;
SwedenSelect.defaultProps = defaultProps;
SwedenSelect.contextTypes = contextTypes;

export default SwedenSelect;
