import React from 'react';
import { shallow } from 'enzyme';
import Loader from '../src/components/Loader';

describe('Loader', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = shallow(<Loader />);
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should render', () => {
    expect(wrapper.find('div').length).toBeGreaterThan(0);
  });

  it('should have loader', () => {
    expect(wrapper.find('.loader').length).toBe(1);
  });

  it('should have loading text', () => {
    const text = wrapper.find('.load').first();
    expect(text.text().length).toBeGreaterThan(0);
  });
});
