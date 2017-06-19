import React from 'react';
import PropTypes from 'prop-types';

const LanguageSelect = (props, context) => {
  const language = context.language.TopBar;
  return (
    <select defaultValue="en" onChange={props.onChange} className="capitalize">
      <option value="sv" className="capitalize">{language.swedish}</option>
      <option value="en" className="capitalize">{language.english}</option>
      <option value="no" className="capitalize">{language.norwegian}</option>
      <option value="da" className="capitalize">{language.danish}</option>
      <option value="is" className="capitalize">{language.icelandic}</option>
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
