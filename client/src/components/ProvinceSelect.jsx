import React from 'react';
import PropTypes from 'prop-types';
import SwedenSelect from './SwedenSelect';
import NorwaySelect from './NorwaySelect';
import DenmarkSelect from './DenmarkSelect';
import IcelandSelect from './IcelandSelect';

const propTypes = {
  onChange: PropTypes.func.isRequired,
  region: PropTypes.string.isRequired,
  showProvince: PropTypes.boolean,
};

const defaultProps = {
  showProvince: false,
};

const ProvinceSelect = ({ onChange, showProvince, region }) => {
  if (region === 'sweden') {
    return (
      <SwedenSelect onChange={onChange} showProvince={showProvince} />
    );
  } else if (region === 'norway') {
    return (
      <NorwaySelect onChange={onChange} showProvince={showProvince} />
    );
  } else if (region === 'iceland') {
    return (
      <IcelandSelect onChange={onChange} showProvince={showProvince} />
    );
  } else if (region === 'denmark') {
    return (
      <DenmarkSelect onChange={onChange} showProvince={showProvince} />
    );
  }
  return (
    null
  );
};

ProvinceSelect.propTypes = propTypes;
ProvinceSelect.defaultProps = defaultProps;

export default ProvinceSelect;
