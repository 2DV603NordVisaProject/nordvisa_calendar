import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  headCode: PropTypes.string.isRequired,
  bodyCode: PropTypes.string.isRequired,
  isGenerated: PropTypes.boolean,
};

const defaultProps = {
  isGenerated: false,
};

const contextTypes = {
  language: PropTypes.object,
};

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

GeneratedOutput.propTypes = propTypes;
GeneratedOutput.defaultProps = defaultProps;
GeneratedOutput.contextTypes = contextTypes;

export default GeneratedOutput;
