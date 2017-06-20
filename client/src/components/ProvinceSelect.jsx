import React from 'react';
import PropTypes from 'prop-types';
import SwedenSelect from './SwedenSelect';
import NorwaySelect from './NorwaySelect';
import DenmarkSelect from './DenmarkSelect';
import IcelandSelect from './IcelandSelect';

const ProvinceSelect = (props) => {
  if (props.region === 'sweden') {
    return (
      <SwedenSelect onChange={props.onChange} showProvince={props.showProvince} />
    );
  } else if (props.region === 'norway') {
    return (
      <NorwaySelect onChange={props.onChange} showProvince={props.showProvince} />
    );
  } else if (props.region === 'iceland') {
    return (
      <IcelandSelect onChange={props.onChange} showProvince={props.showProvince} />
    );
  } else if (props.region === 'denmark') {
    return (
      <DenmarkSelect onChange={props.onChange} showProvince={props.showProvince} />
    );
  }
  return (
    null
  );
};

ProvinceSelect.defaultProps = {
  showProvince: false,
};

ProvinceSelect.propTypes = {
  onChange: PropTypes.func.isRequired,
  region: PropTypes.string.isRequired,
  showProvince: PropTypes.boolean,
};

export default ProvinceSelect;
