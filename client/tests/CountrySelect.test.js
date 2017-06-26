import React from 'react';
import { mount } from 'enzyme';
import CountrySelect from '../src/components/CountrySelect';
import en from '../src/i18n/en';

describe('CountrySelect', () => {
  let wrapper;
  const region = '';
  const onInputChange = jest.fn();
  const context = { language: en };

  beforeEach(() => {
    wrapper = mount(<CountrySelect onInputChange={onInputChange} region={region} />, { context });
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

  it('should have prop region set as a defaultValue', () => {
    const select = wrapper.find('select').first();
    expect(select.props().defaultValue).toBe(region);
  });

  it('should have six options', () => {
    const options = wrapper.find('option');
    expect(options.length).toBe(6);
  });

  it('should have onInputChange prop', () => {
    expect(wrapper.props().onInputChange).toBeDefined();
  });

  describe('user change option', () => {
    wrapper = mount(<CountrySelect onInputChange={onInputChange} region={region} />, { context });
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
