import React from 'react';
import { shallow } from 'enzyme';
import LanguageSelect from '../src/components/LanguageSelect';
import en from '../src/i18n/en';

describe('LanguageSelect', () => {
  let wrapper;
  const context = { language: en };
  const onChange = jest.fn();

  beforeEach(() => {
    wrapper = shallow(<LanguageSelect onChange={onChange} />, { context });
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should render', () => {
    expect(wrapper.find('select').length).toBe(1);
  });

  it('should have options', () => {
    expect(wrapper.find('option').length).toBeGreaterThan(0);
  });

  describe('user changes option', () => {
    wrapper = shallow(<LanguageSelect onChange={onChange} />, { context });
    const input = wrapper.find('select').first();
    const value = 'sv';

    beforeEach(() => {
      input.simulate('change', {
        target: { value },
      });
    });

    it('prop onChange should be called', () => {
      expect(onChange.mock.calls.length).toBe(1);
    });
  });
});
