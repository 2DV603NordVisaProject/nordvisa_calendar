import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  onChange: PropTypes.func.isRequired,
};

const contextTypes = {
  language: PropTypes.object,
};

const LanguageSelect = ({ onChange }, context) => {
  const language = context.language.TopBar;
  const style = {
    textTransform: 'capitalize',
  };
  return (
    <select defaultValue="en" onChange={onChange} style={style}>
      <option value="sv">{language.swedish}</option>
      <option value="en">{language.english}</option>
      <option value="no">{language.norwegian}</option>
      <option value="da">{language.danish}</option>
      <option value="is">{language.icelandic}</option>
    </select>
  );
};

LanguageSelect.propTypes = propTypes;
LanguageSelect.contextTypes = contextTypes;

export default LanguageSelect;
