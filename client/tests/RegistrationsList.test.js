import React from 'react';
import { shallow } from 'enzyme';
import RegistrationsList from '../src/components/RegistrationsList';
import Registration from '../src/components/Registration';
import en from '../src/i18n/en';

describe('RegistrationsList', () => {
  let wrapper;
  const registrations = [1, 2];
  const onInputChange = jest.fn();
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<RegistrationsList
      registrations={registrations}
      onInputChange={onInputChange}
    />,
      { context },
    );
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should render', () => {
    expect(wrapper.find('ul').length).toBeGreaterThan(0);
  });

  it('should pass registration prop to Registration component', () => {
    const registration = wrapper.find('Registration').first();
    expect(registration.props().registration).toBeDefined();
  });

  it('should pass onInputChange prop to  Registration Component', () => {
    const registration = wrapper.find('Registration').first();
    expect(registration.props().onInputChange).toBeDefined();
  });

  it('should have Registration components', () => {
    expect(wrapper.find('Registration').length).toBe(2);
  });
});
