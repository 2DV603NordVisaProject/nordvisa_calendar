import React from 'react';
import PropTypes from 'prop-types';

const CountrySelect = (props, context) => {
  const language = context.language.WidgetView;
  return (
    <select
      className="capitalize"
      name="region"
      onChange={props.onInputChange}
      defaultValue={props.region}
    >
      <option value="" className="capitalize">{language.chooseRegion}</option>
      <option value="all" className="capitalize">{language.allNordic}</option>
      <option value="sweden" className="capitalize">{language.sweden}</option>
      <option value="norway" className="capitalize">{language.norway}</option>
      <option value="denmark" className="capitalize">{language.denmark}</option>
      <option value="iceland" className="capitalize">{language.iceland}</option>
    </select>
  );
};

CountrySelect.propTypes = {
  onInputChange: PropTypes.func.isRequired,
  region: PropTypes.string.isRequired,
};

CountrySelect.contextTypes = {
  language: PropTypes.object,
};

export default CountrySelect;
