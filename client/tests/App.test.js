import React from 'react';
import { shallow } from 'enzyme';
import App from '../src/App';

// TODO; Integration testing for child components

describe('App', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = shallow(<App />);
  });

  it('should render', () => {
    expect(true).toBe(true);
  });
});
