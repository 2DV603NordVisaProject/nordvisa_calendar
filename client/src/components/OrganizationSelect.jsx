import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  onInputChange: PropTypes.func.isRequired,
  fields: PropTypes.shape({
    org: PropTypes.string,
    newOrg: PropTypes.string,
  }).isRequired,
  organizations: PropTypes.arrayOf(
    PropTypes.string,
  ).isRequired,
};

const contextTypes = {
  language: PropTypes.object,
};

const OrganizationSelect = ({ onInputChange, fields, organizations }, context) => {
  const language = context.language.RegisterView;
  return (
    <div>
      <label htmlFor="org" style={{ textTransform: 'capitalize' }}>{language.organization}:</label>
      <select
        name="org"
        style={{ textTransform: 'capitalize' }}
        onChange={onInputChange}
        value={fields.org}
        defaultValue=""
      >
        {
            organizations.map(org => <option value={org}>{org}</option>)
          }
        <option value="new">New Organization</option>
        <option value="">No Organization</option>
      </select>
      <div id="on-select-change" className={fields.org === 'new' ? '' : 'hidden'}>
        <label htmlFor="neworg" style={{ textTransform: 'capitalize' }}>{language.newOrganization}:</label>
        <input
          name="neworg"
          value={fields.neworg}
          onChange={onInputChange}
          type="text"
        />
      </div>
    </div>
  );
};

OrganizationSelect.propTypes = propTypes;
OrganizationSelect.contextTypes = contextTypes;

export default OrganizationSelect;
