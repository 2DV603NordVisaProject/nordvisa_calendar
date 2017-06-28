import React from 'react';
import { mount } from 'enzyme';
import Registration from '../src/components/Registration';
import en from '../src/i18n/en';

describe('Registration', () => {
  let wrapper;
  const context = { language: en };
  const registration = {
    organization: {
      name: '',
      changePending: '',
    },
    email: 'test',
  };
  const onChange = jest.fn();

  beforeEach(() => {
    wrapper = mount(
      <Registration
        onInputChange={onChange}
        registration={registration}
      />,
    { context });
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should render', () => {
    expect(wrapper.find('div').length).toBeGreaterThan(0);
  });

  it('should have a select', () => {
    expect(wrapper.find('select').length).toBe(1);
  });
  it('should have 3 options', () => {
    expect(wrapper.find('option').length).toBe(3);
  });
  it('should have onChange prop', () => {
    expect(wrapper.props().onInputChange).toBeDefined();
  });

  it('should have registration prop', () => {
    expect(wrapper.props().registration).toBeDefined();
  });

  it('should display email', () => {
    const text = wrapper.find('p').at(0).text();
    expect(text).toBe(registration.email);
  });

  it('should display organization name', () => {
    const text = wrapper.find('p').at(1).text();
    expect(text).toBe(registration.organization.name);
  });

  describe('user perform action', () => {
    wrapper = mount(
      <Registration
        onInputChange={onChange}
        registration={registration}
      />,
    { context });
    const input = wrapper.find('select').first();
    beforeEach(() => {
      input.simulate('change');
    });

    it('should call onChange', () => {
      expect(onChange).toHaveBeenCalled();
    });
  });
});
