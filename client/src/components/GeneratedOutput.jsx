import React from 'react';
import PropTypes from 'prop-types';

const GeneratedOutput = ({ isGenerated, headCode, bodyCode }, context) => {
  const language = context.language.WidgetView;
  if (isGenerated) {
    return (
      <div className="code-container">
        <p>{language.headCode}:</p>
        <textarea className="widget-code" defaultValue={headCode} disabled />
        <p>{language.bodyCode}:</p>
        <textarea className="widget-code" defaultValue={bodyCode} disabled />
      </div>
    );
  }
  return null;
};

GeneratedOutput.propTypes = {
  headCode: PropTypes.string.isRequired,
  bodyCode: PropTypes.string.isRequired,
  isGenerated: PropTypes.boolean,
};

GeneratedOutput.defaultProps = {
  isGenerated: false,
};

GeneratedOutput.contextTypes = {
  language: PropTypes.object,
};

export default GeneratedOutput;
