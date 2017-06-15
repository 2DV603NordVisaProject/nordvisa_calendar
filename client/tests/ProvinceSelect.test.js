/*
- Region conditional
- Is defined
- Is rendered
 */
import React from 'react';
import { shallow } from 'enzyme';
import en from '../src/i18n/en';
import ProvinceSelect from '../src/components/ProvinceSelect';

describe('ProvinceSelect', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<ProvinceSelect />, { context });
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  describe('prop region is sweden', () => {
    beforeEach(() => {
      wrapper = shallow(<ProvinceSelect region="sweden" />, { context });
    });

    it('should show dropdown', () => {
      expect(wrapper.find('select').length).toBe(1);
    });
  });

  describe('prop region is norway', () => {
    beforeEach(() => {
      wrapper = shallow(<ProvinceSelect region="norway" />, { context });
    });

    it('should show dropdown', () => {
      expect(wrapper.find('select').length).toBe(1);
    });
  });

  describe('prop region is denmark', () => {
    beforeEach(() => {
      wrapper = shallow(<ProvinceSelect region="denmark" />, { context });
    });

    it('should show dropdown', () => {
      expect(wrapper.find('select').length).toBe(1);
    });
  });

  describe('prop region is iceland', () => {
    beforeEach(() => {
      wrapper = shallow(<ProvinceSelect region="iceland" />, { context });
    });

    it('should show dropdown', () => {
      expect(wrapper.find('select').length).toBe(1);
    });
  });
});
