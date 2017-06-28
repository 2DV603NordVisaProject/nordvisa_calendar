import React from 'react';
import { shallow } from 'enzyme';
import DenmarkSelect from '../src/components/DenmarkSelect';
import en from '../src/i18n/en';

describe('DenmarkSelect', () => {
  let wrapper;
  const onChange = jest.fn();
  const context = { language: en };

  describe('showProvince prop is false', () => {
    beforeEach(() => {
      wrapper = shallow(<DenmarkSelect
        showProvince={false}
        onChange={onChange}
      />,
        { context },
      );
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should render nothing', () => {
      expect(wrapper.find('select').length).toBe(0);
    });
  });

  describe('showProvince prop is true', () => {
    beforeEach(() => {
      wrapper = shallow(<DenmarkSelect
        onChange={onChange}
        showProvince
      />,
        { context },
      );
    });

    it('should render', () => {
      expect(wrapper.find('select').length).toBe(1);
    });

    it('should have options', () => {
      expect(wrapper.find('option').length).toBeGreaterThan(0);
    });

    describe('user change option', () => {
      wrapper = shallow(<DenmarkSelect
        onChange={onChange}
        showProvince
      />,
        { context },
      );
      const input = wrapper.find('select').first();
      const value = 'nordjylland';

      beforeEach(() => {
        input.simulate('change', {
          target: { value },
        });
      });

      it('prop onChange should be called', () => {
        const calls = onChange.mock.calls.length;
        expect(calls).toBe(1);
      });
    });
  });
});
