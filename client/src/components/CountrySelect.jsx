import React from 'react';
import PropTypes from 'prop-types';

const CountrySelect = ({ onInputChange, region }, context) => {
  const language = context.language.WidgetView;
  return (
    <select
      style={{ textTransform: 'capitalize' }}
      name="region"
      onChange={onInputChange}
      defaultValue={region}
    >
      <option value="">{language.chooseRegion}</option>
      <option value="all">{language.allNordic}</option>
      <option value="sweden">{language.sweden}</option>
      <option value="norway">{language.norway}</option>
      <option value="denmark">{language.denmark}</option>
      <option value="iceland">{language.iceland}</option>
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
