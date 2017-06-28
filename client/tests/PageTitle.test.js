import React from 'react';
import { mount } from 'enzyme';
import PageTitle from '../src/components/PageTitle';

describe('PageTitle', () => {
  let wrapper;
  const title = 'Hello World';

  beforeEach(() => {
    wrapper = mount(<PageTitle>{title}</PageTitle>);
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should render', () => {
    expect(wrapper.find('h2').length).toBe(1);
  });

  it('should receive children props', () => {
    expect(wrapper.props().children).toBeDefined();
  });

  it('should display title-text', () => {
    const text = wrapper.find('h2').first().text();
    expect(text).toBe(title);
  });
});
