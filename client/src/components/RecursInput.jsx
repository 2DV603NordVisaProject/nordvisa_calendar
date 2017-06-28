import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  fields: PropTypes.shape({
    reccuring: PropTypes.string,
    recursuntil: PropTypes.string,
    recurs: PropTypes.string,
  }).isRequired,
  onInputChange: PropTypes.func.isRequired,
  showRecursInput: PropTypes.boolean,
};

const defaultProps = {
  showRecursInput: false,
};

const contextTypes = {
  language: PropTypes.object,
};

const RecursInput = ({ fields, onInputChange, showRecursInput }, context) => {
  const language = context.language.CreateView;
  return (
    <div>
      { showRecursInput ? null : (
        <div className="recurring">
          <label htmlFor="recurring" style={{ textTransform: 'capitalize' }}>{language.recurring}:</label>
          <input
            name="recurring"
            type="checkbox"
            className="approve"
            checked={fields.recurring}
            onChange={onInputChange}
          />
        </div>
      )}
      { showRecursInput ? (
        <div className="is-recurring">
          <label htmlFor="recursuntil" style={{ textTransform: 'capitalize' }}>{language.recursUntil}:</label>
          <input
            type="date"
            name="recursuntil"
            value={fields.recursuntil}
            onChange={onInputChange}
          />
          <label htmlFor="recurs" style={{ textTransform: 'capitalize' }}>{language.recurs}:</label>
          <select
            className="capitalize"
            name="recurs"
            value={fields.recurs}
            onChange={onInputChange}
          >
            <option selected style={{ textTransform: 'capitalize' }} value="0">{language.weekly}</option>
            <option className="capitalize" value="1">{language.monthly}</option>
            <option className="capitalize" value="2">{language.yearly}</option>
          </select>
        </div>
      ) : null}
    </div>
  );
};

RecursInput.propTypes = propTypes;
RecursInput.defaultProps = defaultProps;
RecursInput.contextTypes = contextTypes;

export default RecursInput;
