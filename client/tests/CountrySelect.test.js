import React from 'react';
import { shallow } from 'enzyme';
import CountrySelect from '../src/components/CountrySelect';
import en from '../src/i18n/en';

describe('CountrySelect', () => {
  let wrapper;
  const region = '';
  const onInputChange = jest.fn();
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<CountrySelect onInputChange={onInputChange} region={region} />, { context });
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should render', () => {
    expect(wrapper.find('select').length).toBe(1);
  });

  it('should receive region prop', () => {
    expect(wrapper.props().region).toBe('');
  });

  it('should have onInputChange prop', () => {
    expect(wrapper.props().onInputChange).toBeDefined();
  });

  describe('user change option', () => {
    const input = wrapper.find('select').first();
    const value = 'sweden';

    beforeEach(() => {
      input.simulate('change', {
        target: { value },
      });
    });

    it('onInputChange should be called', () => {
      expect(onInputChange.mock.calls.length).toBe(1);
    });
  });
});
