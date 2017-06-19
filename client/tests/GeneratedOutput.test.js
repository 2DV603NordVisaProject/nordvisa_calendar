import React from 'react';
import { shallow } from 'enzyme';
import GeneratedOutput from '../src/components/GeneratedOutput';

describe('GeneratedOutput', () => {
  let wrapper;

  describe('is not generated', () => {
    beforeEach(() => {
      wrapper = shallow(<GeneratedOutput isGenerated headCode="" bodyCode="" />);
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should not render', () => {
      expect(wrapper.find('textarea').length).toBe(0);
    });
  });

  describe('is generated', () => {
    it('should render', () => {
      expect(wrapper.find('textarea').length).toBe(2);
    });
  });
});
