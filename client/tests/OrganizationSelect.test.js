import React from 'react';
import { shallow } from 'enzyme';
import OrganizationSelect from '../src/components/OrganizationSelect';
import en from '../src/i18n/en';

describe('OrganizationSelect', () => {
  const context = { language: en };
  const fields = {
    org: '',
    neworg: 'world',
  };
  const onInputChange = jest.fn();
  let wrapper;
  let organizations;

  describe('organizations prop is empty', () => {
    beforeEach(() => {
      organizations = [];
      wrapper = shallow(
        <OrganizationSelect
          organizations={organizations}
          fields={fields}
          onInputChange={onInputChange}
        />,
      { context });
    });

    afterEach(() => {
      onInputChange.mockClear();
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should render', () => {
      expect(wrapper.find('select').length).toBeGreaterThan(0);
    });

    it('should have 2 options', () => {
      expect(wrapper.find('option').length).toBe(2);
    });
  });

  describe('prop organizations contains 2 organizations', () => {
    beforeEach(() => {
      organizations = [1, 2];
      wrapper = shallow(
        <OrganizationSelect
          organizations={organizations}
          fields={fields}
          onInputChange={onInputChange}
        />,
      { context });
    });

    it('should have 4 options', () => {
      expect(wrapper.find('option').length).toBe(4);
    });

    it('should only have 1 select', () => {
      expect(wrapper.find('select').length).toBe(1);
    });

    it('first select should have fields.org as value', () => {
      const input = wrapper.find('select').first();
      expect(input.props().value).toBe(fields.org);
    });

    describe('user changes option', () => {
      const value = 'test value';
      beforeEach(() => {
        wrapper = shallow(
          <OrganizationSelect
            organizations={organizations}
            fields={fields}
            onInputChange={onInputChange}
          />,
        { context });
        const input = wrapper.find('select').first();
        input.simulate('change', {
          target: { value },
        });
      });

      it('should call onInputChange prop', () => {
        expect(onInputChange).toHaveBeenCalled();
      });
    });

    describe('fields.org prop has value "new"', () => {
      fields.org = 'new';
      beforeEach(() => {
        wrapper = shallow(
          <OrganizationSelect
            organizations={organizations}
            fields={fields}
            onInputChange={onInputChange}
          />,
        { context });
      });

      it('should have 1 input', () => {
        expect(wrapper.find('input').length).toBe(1);
      });

      it('should have fields.neworg as value', () => {
        const input = wrapper.find('input').first();
        expect(input.props().value).toBe(fields.neworg);
      });

      describe('user enters a name of a new org', () => {
        beforeEach(() => {
          wrapper = shallow(
            <OrganizationSelect
              organizations={organizations}
              fields={fields}
              onInputChange={onInputChange}
            />,
          { context });
          const input = wrapper.find('input').first();
          const value = 'new org';

          input.simulate('change', {
            target: { value },
          });
        });

        it('should call onInputChange prop', () => {
          expect(onInputChange).toHaveBeenCalled();
        });
      });
    });
  });
});
