import React from 'react';
import { shallow } from 'enzyme';
import ErrorList from '../src/components/ErrorList';

describe('ErrorList', () => {
  let wrapper;
  const errors = ['sample error one', 'sample error two'];

  beforeEach(() => {
    wrapper = shallow(<ErrorList errors={errors} />);
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should be rendered', () => {
    expect(wrapper.find('ul').length).toBe(1);
  });

  it('should contain error message', () => {
    const error = wrapper.find('li').at(1);
    expect(error.text()).toBe(errors[1]);
  });

  it('should print all error message', () => {
    expect(wrapper.find('li').length).toBe(2);
  });

  it('should not have any long errors', () => {
    expect(wrapper.find('.long-error').length).toBe(0);
  });

  describe('added long error', () => {
    const longErrors = [];
    longErrors.push('a very very long sample error that has a very very long message!');

    beforeEach(() => {
      wrapper = shallow(<ErrorList errors={longErrors} />);
    });

    it('should have any long errors', () => {
      expect(wrapper.find('.long-error').length).toBe(1);
    });
  });
});
