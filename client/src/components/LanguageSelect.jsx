import React from 'react';
import PropTypes from 'prop-types';

const LanguageSelect = (props, context) => {
  const language = context.language.TopBar;
  const style = {
    textTransform: 'capitalize',
  };
  return (
    <select defaultValue="en" onChange={props.onChange} style={style}>
      <option value="sv">{language.swedish}</option>
      <option value="en">{language.english}</option>
      <option value="no">{language.norwegian}</option>
      <option value="da">{language.danish}</option>
      <option value="is">{language.icelandic}</option>
    </select>
  );
};

LanguageSelect.propTypes = {
  onChange: PropTypes.func.isRequired,
};

LanguageSelect.contextTypes = {
  language: PropTypes.object,
};

export default LanguageSelect;
